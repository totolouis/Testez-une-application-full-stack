describe('Register spec', () => {
  it('Register successfully', () => {
    cy.visit('/register')

    cy.intercept('POST', '/api/auth/register', {
      body: {
        id: 1,
        username: 'newUser',
        firstName: 'New',
        lastName: 'User',
        email: 'newuser@example.com',
        admin: false
      },
    })

    cy.get('input[formControlName=firstName]').type("New")
    cy.get('input[formControlName=lastName]').type("User")
    cy.get('input[formControlName=email]').type("newuser@example.com")
    cy.get('input[formControlName=password]').type("test!1234{enter}")

    cy.url().should('include', '/login')
  })
});
