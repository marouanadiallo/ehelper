import { UserForm } from "./models";
import {
    firstNameValidatorFn,
    lastNameValidatorFn,
    emailValidatorFn,
    telephoneValidatorFn,
    birthDateValidatorFn,
    genderValidatorFn
} from "./user-form-validators";

declare const Alpine: any;
declare type ErrorFields = {
    gender: {message: string | '', valid: boolean };
    firstName: {message: string | '', valid: boolean };
    lastName: {message: string | '', valid: boolean };
    email: {message: string | '', valid: boolean };
    telephone: {message: string | '', valid: boolean };
    birthDate: {message: string | '', valid: boolean };
};

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
