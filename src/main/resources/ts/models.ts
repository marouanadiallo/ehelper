export function isKeyOf<T>(key: string, obj: T): key is keyof T {
  return key in obj;
}

export declare type UserForm = {
    gender: "HOMME" | "FEMME" | null;
    firstName: string;
    lastName: string;
    email: string;
    telephone: string;
    birthDate: string;
};
