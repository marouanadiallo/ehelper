import {  UserForm } from "./models.ts";

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

document.addEventListener('alpine:init', () => {
    Alpine.data('userIndexPage', () => ({
        loading: false,
        file: null as File | null,
        fileInput: null as HTMLInputElement | null,
        showPreviewModal: false,
        users: [] as Array<UserForm>,
        
        resetAll() {
            this.file = null;
            this.fileInput!.value = '';
            this.showPreviewModal = false;
            this.users = [];
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
                alert('Invalid file type. Please upload a CSV file.');
                fileInput.value = '';
                return;
            }
            if (!validFileSize(this.file)) {
                alert('File size exceeds the 10 MB limit.');
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
            this.users = lines.slice(1).map(line => {
                const values = line.split(DELIMITER).map(v => v.trim());
                const user: any = {};
                headers.forEach((header, index) => {
                    user[header] = values[index] || '';
                });
                return user as UserForm;
            });
        }
    }));
});