document.addEventListener('alpine:init', () => {
    Alpine.data('userIndexPage', () => ({
        loading: false,
        init() {
            console.log('User index page initialized');
        }
    }));
});