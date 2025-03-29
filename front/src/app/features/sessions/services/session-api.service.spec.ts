import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService]
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all sessions', () => {
    const dummySessions: Session[] = [
      { id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), users: [], teacher_id: 1 },
      { id: 2, name: 'Session 2', description: 'Description 2', date: new Date(), users: [], teacher_id: 1 },
    ];

    service.all().subscribe(sessions => {
      expect(sessions.length).toBe(2);
      expect(sessions).toEqual(dummySessions);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(dummySessions);
  });

  it('should fetch session details', () => {
    const dummySession: Session =
        { id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), users: [], teacher_id: 1 };

    service.detail('1').subscribe(session => {
      expect(session).toEqual(dummySession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(dummySession);
  });

  it('should delete a session', () => {
    service.delete('1').subscribe(response => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should create a session', () => {
    const newSession: Session =
        { id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), users: [], teacher_id: 1 };

    service.create(newSession).subscribe(session => {
      expect(session).toEqual(newSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    req.flush(newSession);
  });

  it('should update a session', () => {
    const updatedSession: Session =
        { id: 1, name: 'Updated Session 1', description: 'Updated Description 1', date: new Date(), users: [], teacher_id: 1 };

    service.update('1', updatedSession).subscribe(session => {
      expect(session).toEqual(updatedSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    req.flush(updatedSession);
  });

  it('should participate in a session', () => {
    service.participate('1', '2').subscribe(response => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne('api/session/1/participate/2');
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should unparticipate from a session', () => {
    service.unParticipate('1', '2').subscribe(response => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne('api/session/1/participate/2');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});
