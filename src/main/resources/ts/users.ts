document.addEventListener('alpine:init', () => {
    Alpine.data('users', () => ({
        init() {
            console.log('Users module initialized');
        }
    }));
});