//TODO: need to update no?

describe('Update Session spec', () => {
  beforeEach(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
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

    cy.get('input[formControlName=email]').type("yoga@studio.com");
    cy.get('input[formControlName=password]').type("test!1234{enter}{enter}");

    cy.intercept('GET', '/api/teacher', {
      body: [
        {
          id: 1,
          firstName: 'Teacher',
          lastName: 'User'
        }
      ]
    }).as('getTeachers');

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
    cy.intercept('PUT', '/api/session/1', {
      body: {
        id: 1,
        name: 'Updated Session',
        description: 'Updated Description',
        date: '2023-01-01T00:00:00.000Z',
        teacher_id: 1,
        users: []
      }
    }).as('getSession');
  });

  it('Update session successfully', () => {
    cy.contains('button', 'Edit').click()

    cy.wait('@getSession');

    cy.get('input[formControlName=name]').clear().type("Updated Session");
    cy.get('textarea[formControlName=description]').clear().type("Updated Description");
    cy.get('input[formControlName=date]').clear().type("2025-03-25");
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('Teacher User').click();
    cy.intercept('GET', '/api/session', {
      body: [
        {
          id: 1,
          name: 'Updated Session',
          description: 'Updated Description',
          date: '2023-01-01T00:00:00.000Z',
          teacher_id: 1,
          users: []
        }
      ]
    }).as('session2');
    cy.get('button[type=submit]').click();

    cy.url().should('include', '/sessions');
    cy.contains('Updated Session');
    cy.contains('Updated Description');
  });
});
