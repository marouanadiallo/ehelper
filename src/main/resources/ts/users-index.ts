import createUserBatch from "./create-user-batch";

declare const Alpine: any;

document.addEventListener('alpine:init', () => {
    Alpine.data('uploadUserBatch', createUserBatch);
});
