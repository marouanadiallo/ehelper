import { UserForm } from "./models.ts";

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
    const EMAIL_REGEXP = /^(?=.{1,254}$)(?=.{1,64}@)[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
    const err = email && EMAIL_REGEXP.test(email);
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

export {
    firstNameValidatorFn,
    lastNameValidatorFn,
    emailValidatorFn,
    telephoneValidatorFn,
    birthDateValidatorFn,
    genderValidatorFn
};