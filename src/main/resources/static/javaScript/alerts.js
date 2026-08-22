
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".alert-auto-close").forEach(alert => {
        setTimeout(() => {
            bootstrap.Alert.getOrCreateInstance(alert).close();
        }, 3000);
    });
});