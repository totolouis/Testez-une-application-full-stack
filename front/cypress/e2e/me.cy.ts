describe('MeComponent', () => {
  beforeEach(() => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    }).as('login')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.wait('@login')

    // Mock the user data
    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 1,
        firstName: 'Test',
        lastName: 'User',
        email: 'test@example.com',
        admin: false,
        createdAt: '2023-01-01T00:00:00.000Z',
        updatedAt: '2023-01-01T00:00:00.000Z'
      }
    }).as('getUser');

    cy.contains('span', 'Account').click()
  });

  it('should display user information', () => {
    cy.get('p').contains('Name: Test USER');
    cy.get('p').contains('Email: test@example.com');
    cy.get('p').contains('Create at: January 1, 2023');
    cy.get('p').contains('Last update: January 1, 2023');
  });

  it('should navigate back when back button is clicked', () => {
    cy.get('button[mat-icon-button]').click();
    cy.url().should('not.include', '/me');
  });

  it('should delete user and navigate to home', () => {
    cy.intercept('DELETE', '/api/user/1', {}).as('deleteUser');

    cy.get('button').contains('Detail').click();
    cy.wait('@deleteUser');

    cy.get('simple-snack-bar').contains('Your account has been deleted !');
    cy.url().should('include', '/');
  });
});
