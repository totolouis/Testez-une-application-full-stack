describe('Participate Session spec', () => {
  beforeEach(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 2,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    });

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
    }).as('session');

    cy.get('input[formControlName=email]').type("louis@eleve.com");
    cy.get('input[formControlName=password]').type("test!12324{enter}{enter}");

    cy.intercept('GET', '/api/teacher/1', {
      body: {
        id: 1,
        firstName: 'Teacher',
        lastName: 'User'
      }
    }).as('getTeacher');

    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'Existing Session',
        description: 'Existing Description',
        date: '2023-01-01T00:00:00.000Z',
        teacher_id: 1,
        users: []
      }
    }).as('getSession');
  });

  it('Participate session successfully', () => {
    cy.intercept('POST', '/api/session/1/participate/2', {
      statusCode: 200,
      body: {}
    }).as('getSessionParticipation');

    cy.contains('button', 'Detail').click();

    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'Existing Session',
        description: 'Existing Description',
        date: '2023-01-01T00:00:00.000Z',
        teacher_id: 1,
        users: [2]
      }
    }).as('getSession2');

    cy.contains('button', 'Participate').click();

    // Verify the update
    cy.url().should('include', '/sessions/detail/1');
    cy.contains('Existing Description');
  });
});
