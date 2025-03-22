import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in a user', () => {
    const user: SessionInformation = { token: 'token', type: 'type', id: 1, username: 'username', firstName: 'firstName', lastName: 'lastName', admin: true };
    service.logIn(user);
    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(user);
  });

  it('should log out a user', () => {
    service.logOut();
    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit the correct logged in status', (done) => {
    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(false);
      done();
    });
  });

  it('should emit the correct logged in status after login', (done) => {
    const user: SessionInformation = { token: 'token', type: 'type', id: 1, username: 'username', firstName: 'firstName', lastName: 'lastName', admin: true };
    service.logIn(user);
    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(true);
      done();
    });
  });

  it('should emit the correct logged in status after logout', (done) => {
    service.logOut();
    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(false);
      done();
    });
  });
});
