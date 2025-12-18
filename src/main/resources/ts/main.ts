import 'scss/styles.css';

document.addEventListener('alpine:init', () => {
    Alpine.data('mainOfApp', () => ({
        init() {
            console.log('From main.ts initialized');
        }
    }));
});