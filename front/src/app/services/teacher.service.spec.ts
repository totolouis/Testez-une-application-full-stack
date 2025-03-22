import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {expect} from '@jest/globals';
import {TeacherService} from './teacher.service';
import {Teacher} from '../interfaces/teacher.interface';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService]
    });
    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all teachers', () => {
    const mockTeachers: Teacher[] = [
      {id: 1, lastName: 'Teacher', firstName: 'One', createdAt: new Date(), updatedAt: new Date()},
      {id: 2, lastName: 'Teacher2', firstName: 'Two', createdAt: new Date(), updatedAt: new Date()}
    ];

    service.all().subscribe(teachers => {
      expect(teachers).toEqual(mockTeachers);
    });

    const req = httpMock.expectOne('api/teacher');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeachers);
  });

  it('should fetch teacher by id', () => {
    const mockTeacher: Teacher = {
      id: 1,
      lastName: 'Teacher',
      firstName: 'One',
      createdAt: new Date(),
      updatedAt: new Date()
    };

    service.detail('1').subscribe(teacher => {
      expect(teacher).toEqual(mockTeacher);
    });

    const req = httpMock.expectOne('api/teacher/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeacher);
  });
});
