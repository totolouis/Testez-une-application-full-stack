describe('Update Session spec', () => {
  it('Update session successfully', () => {
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

    cy.intercept('GET', '/api/session', {
      body: [
        {
          id: 1,
          name: 'Existing Session',
          description: 'Existing Description',
          date: '2023-01-01T00:00:00.000Z',
          teacher_id: 1,
          users: []
        }
      ]
    }).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

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

    // Mock the session data to be updated
    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'Existing Session',
        description: 'Existing Description',
        date: '2023-01-01T00:00:00.000Z',
        teacher_id: 1,
        users: [1]
      }
    }).as('getSession')

    cy.contains('button', 'Detail').click()

    // Verify the update
    cy.url().should('include', '/sessions/detail/1')
    cy.contains('Existing Description')
  })
});
