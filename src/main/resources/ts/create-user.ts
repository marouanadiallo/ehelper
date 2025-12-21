declare const Alpine: any;

declare type UserForm = {
    gender: "HOMME" | "FEMME" | null;
    firstName: string;
    lastName: string;
    email: string;
    telephone: string;
    birthDate: string;
}

declare type ErrorFields = {
    gender: {message: string | '', valid: boolean };
    firstName: {message: string | '', valid: boolean };
    lastName: {message: string | '', valid: boolean };
    email: {message: string | '', valid: boolean };
    telephone: {message: string | '', valid: boolean };
    birthDate: {message: string | '', valid: boolean };
};

function firstNameValidatorFn(firstName: UserForm["firstName"]): string {
    const firstNameRegex = /^[a-zA-ZàâäéèêëîïôöùûüÿçœæÀÂÄÉÈÊËÎÏÔÖÙÛÜŸÇŒÆ\-' \s]{2,75}$/;
    const err = firstName && firstName.trim().length >= 2 && firstNameRegex.test(firstName);
    return err ? '' : 'Le prénom doit contenir entre 2 et 50 caractères valides.'
}

// last name validator
function lastNameValidatorFn(lastName: UserForm["lastName"]): string {
    const lastNameRegex = /^[a-zA-ZàâäéèêëîïôöùûüÿçœæÀÂÄÉÈÊËÎÏÔÖÙÛÜŸÇŒÆ\-'\s]{2,50}$/;
    const err = lastName && lastName.trim().length >= 2 && lastNameRegex.test(lastName);
    return err ? '' : 'Le nom doit contenir entre 2 et 50 caractères valides.';
}

// email validator
function emailValidatorFn(email: UserForm["email"]): string {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const err = email && emailRegex.test(email);
    return err ? '' : 'Veuillez entrer une adresse email valide.';
}

// telephone validator
function telephoneValidatorFn(telephone: UserForm["telephone"]): string {
    const phoneRegex = /^(?:\+224|0)?6[0-9]{8}$/;
    const err = telephone && phoneRegex.test(telephone);
    return err ? '' : 'Veuillez entrer un numéro de téléphone valide (ex: 610859632 ou +224610859632).';
}

function birthDateValidatorFn(birthDate: UserForm["birthDate"]): string {
    if (!birthDate) {
        return "La date de naissance est obligatoire";
    }

    const today = new Date();
    const birth = new Date(birthDate);
    
    // Calcul Age
    let age = today.getFullYear() - birth.getFullYear();
    const monthDiff = today.getMonth() - birth.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
        age--;
    }

    // futur date
    if (birth > today) {
        return "La date de naissance ne peut pas être dans le futur";
    }

    // Min 18 olds
    if (age < 18) {
        return "L'utilisateur doit avoir au moins 18 ans";
    }

    // Max 80 olds
    if (age > 80) {
        return "L'utilisateur ne peut pas avoir plus de 80 ans";
    }

    return '';
}

function genderValidatorFn(gender: "HOMME" | "FEMME" | null | undefined): boolean {
    if (!gender) return false;
    return "HOMME" === gender.toUpperCase() || gender.toUpperCase() === "FEMME";
}

document.addEventListener('alpine:init', () => {
    Alpine.data('userForm', () => ({
        ffields: <UserForm> {},
        errors: <ErrorFields> {
            gender: {message: '', valid: false },
            firstName: {message: '', valid: false },
            lastName: {message: '', valid: false },
            email: {message: '', valid: false },
            telephone: {message: '', valid: false },
            birthDate: {message: '', valid: false },
        },
        isInvalid: true,
        
        validateField(field: keyof UserForm) {
            switch (field) {
                case 'firstName':
                    const firstNameError = firstNameValidatorFn(this.ffields.firstName);
                    this.errors.firstName = { message: firstNameError, valid: firstNameError === '' };
                    break;
                case 'lastName':
                    const lastNameError = lastNameValidatorFn(this.ffields.lastName);
                    this.errors.lastName = { message: lastNameError, valid: lastNameError === '' };
                    break;
                case 'email':
                    const emailError = emailValidatorFn(this.ffields.email);
                    this.errors.email = { message: emailError, valid: emailError === '' };
                    break;
                case 'telephone':
                    const telephoneError = telephoneValidatorFn(this.ffields.telephone);
                    this.errors.telephone = { message: telephoneError, valid: telephoneError === '' };
                    break;
                case 'birthDate':
                    const birthDateError = birthDateValidatorFn(this.ffields.birthDate);
                    this.errors.birthDate = { message: birthDateError, valid: birthDateError === '' };
                    break;
            }
            this.checkFormValidity();
        },

        checkFormValidity() {
            const isGenderValid = genderValidatorFn(this.ffields.gender);
            this.errors.gender = { message: isGenderValid ? '' : 'Veuillez sélectionner un genre.', valid: isGenderValid };
            this.isInvalid = Object.values(this.errors).some(err => !err.valid);
            console.log({form: this.ffields, errors: this.errors, isInvalid: this.isInvalid});
        }
    }));
});
