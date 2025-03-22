describe('Create Session spec', () => {
  it('Create session successfully', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.wait(100);

    // Mock the return of the teacher list
    cy.intercept('GET', '/api/teacher', {
      body: [
        {
          id: 1,
          firstName: 'Teacher',
          lastName: 'User'
        }
      ]
    }).as('getTeachers')

    cy.contains('button', 'Create').click()

    cy.intercept('POST', '/api/session', {
      body: {
        id: 1,
        name: 'New Session',
        description: 'Session Description',
        date: '2023-01-01T00:00:00.000Z',
        teacher_id: 1,
        users: []
      },
    })

    cy.get('input[formControlName=name]').type("New Session")
    cy.get('textarea[formControlName=description]').type("Session Description")
    cy.get('input[formControlName=date]').type("2025-03-25")
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('Teacher User').click();
    cy.get('button[type=submit]').click()

    cy.url().should('include', '/sessions')
  })
});
