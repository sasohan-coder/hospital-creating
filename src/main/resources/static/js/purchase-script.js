document.addEventListener('DOMContentLoaded', () => {
    // Sidebar Toggle
    const menuToggle = document.querySelector('.menu-toggle');
    const sidebar = document.querySelector('.sidebar');
    menuToggle?.addEventListener('click', () => sidebar.classList.toggle('active'));

    // Add New Appointment Modal
    const addButton = document.querySelector('.btn-primary');
    const modal = document.getElementById('addPurchaseModal');
    const modalClose = document.querySelector('.modal-close');
    const btnCancel = document.querySelector('.btn-cancel');

    addButton?.addEventListener('click', () => {
        document.querySelector('.modal-form').reset();
        modal.classList.add('active');
    });

    modalClose?.addEventListener('click', () => modal.classList.remove('active'));
    btnCancel?.addEventListener('click', () => modal.classList.remove('active'));
    modal?.addEventListener('click', (e) => {
        if (e.target === modal) modal.classList.remove('active');
    });

    // Datepicker
    flatpickr('#appointmentDate', { dateFormat: 'Y-m-d', defaultDate: new Date() });
});
