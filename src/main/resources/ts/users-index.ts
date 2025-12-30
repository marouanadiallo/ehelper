import {  UserForm, isKeyOf } from "./models.ts";
import {
    firstNameValidatorFn,
    lastNameValidatorFn,
    emailValidatorFn,
    telephoneValidatorFn,
    birthDateValidatorFn,
    genderValidatorFn
} from "./user-form-validators.ts";

declare const Alpine: any;
const fileTypes = ['text/csv', '.csv'];
const DELIMITER = ',';

function validFileType(file: File) {
  return fileTypes.includes(file.type);
}

function validFileSize(file: File) {
  const maxSize = 10 * 1e6; // 10 MB
  return file.size <= maxSize;
}

function returnFileSize(size: number): string {
  if (size < 1e3) {
    return `${size} bytes`;
  } else if (size >= 1e3 && size < 1e6) {
    return `${(size / 1e3).toFixed(1)} KB`;
  }
  return `${(size / 1e6).toFixed(1)} MB`;
}

function headerKeysAreValid(headers: Array<string>): boolean {
  let valid = true;
  headers.forEach(header => {
    if(!isKeyOf<UserForm>(header, {
        gender: null,
        firstName: '',
        lastName: '',
        email: '',
        telephone: '',
        birthDate: '',
    })) {
            valid = false;
        }
    });
    return valid;
}

function isValidColonneValue(position: number, value: string): any {
    switch(position) {
        case 0:
           const genderError = genderValidatorFn(value as 'HOMME' | 'FEMME' | null);
           return `genre invalide (${genderError ? null : 'seulement HOMME ou FEMME  est accepté'})`;
        case 1:
           const firstNameError = lastNameValidatorFn(value);
           return firstNameError === '' ? null : `prénom invalide (${firstNameError})`;
        case 2:
           const lastNameError = firstNameValidatorFn(value);
           return lastNameError === '' ? null : `nom invalide (${lastNameError})`;
        case 3:
           const emailError = emailValidatorFn(value);
           return emailError === '' ? null : `email invalide (${emailError})`;
        case 4:
           const telephoneError = telephoneValidatorFn(value);
           return telephoneError === '' ? null : `téléphone invalide (${telephoneError})`;
        case 5:
           const birthDateError = birthDateValidatorFn(value);
           return birthDateError === '' ? null : `date de naissance invalide (${birthDateError})`;
        default:
           return null;
    }
}

document.addEventListener('alpine:init', () => {
    Alpine.data('userIndexPage', () => ({
        loading: false,
        file: null as File | null,
        fileInput: null as HTMLInputElement | null,
        showPreviewModal: false,
        users: [] as Array<UserForm>,
        errors: [] as Array<string>,
        
        /**         
         * This function resets all the data related to file upload and user preview.
        */
        resetAll() {
            this.file = null;
            this.fileInput!.value = '';
            this.showPreviewModal = false;
            this.users = [];
            this.errors = [];
        },

        /**
         * This function previews the user batch upload by reading the selected file,
         * validating its type and size, and parsing its content if valid.
         * @param $event change event of input file
         * @returns nothing if file invalid
        */
        previewUserBatchUpload($event: any) {
            const fileInput = $event.target as HTMLInputElement;
            this.file = fileInput.files?.[0] as File;
            if (!this.file) {
                return;
            }
            if (!validFileType(this.file)) {
                this.errors = ['Fichier non valide. Veuillez télécharger un fichier CSV.'];
                fileInput.value = '';
                return;
            }
            if (!validFileSize(this.file)) {
                this.errors = ['La taille du fichier dépasse la limite de 10 Mo.'];
                fileInput.value = '';
                return;
            }
            this.showPreviewModal = true;
            this.fileInput = fileInput;
            const reader = new FileReader();
            reader.onload = (e) => {
                const text = e.target?.result as string;
                this.parseCSV(text);
            };
            reader.readAsText(this.file);
        },

        /**
         * This function parses a CSV text and populates the users array.
         * @param text line of user
        */
        parseCSV(text: string) {
            const lines = text.split('\n').filter(line => line.trim() !== '');
            const headers = lines[0].split(DELIMITER).map(h => h.trim());
            
            // Validate headers
            if(!headerKeysAreValid(headers)) {
                this.errors.push('Les en-têtes du fichier CSV ne correspondent pas aux champs attendus.');
                this.errors.push('Champs attendus : gender, firstName, lastName, email, telephone, birthDate.');
                return;
            }

            // Parse lines
            this.users = [];
            this.errors = [];
            lines.slice(1).some((line, index) => {
                const values = line.split(DELIMITER).map(v => v.trim());
                if (values.length !== headers.length) {
                    this.errors.push(`Nombre de colonnes incorrect à la ligne ${index + 2}.`);
                    return true; // break the loop
                }

                const user: any = {};
                let lineHasError = false;

                headers.forEach((header, i) => {
                   const errorMessage = isValidColonneValue(i, values[i]);
                    if (errorMessage) {
                        this.errors.push(`Ligne ${index + 2} : ${errorMessage}`);
                        lineHasError = true;
                    }
                    user[header] = values[i];
                });
                
                if(lineHasError) {
                    this.users = []; // clear users if any error in line
                    return true; // break the loop
                }
                this.users.push(user as UserForm);
                return false; // continue the loop
            });
        }
    }));
});