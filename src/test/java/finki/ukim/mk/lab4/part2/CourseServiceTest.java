package finki.ukim.mk.lab4.part2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Nested
    @DisplayName("findActiveCourses")
    class FindActiveCoursesTests {

        @Test
        @DisplayName("should return only active courses when repository returns mixed statuses")
        void shouldReturnOnlyActiveCourses() {
            Course activeCourse1 = new Course(1L, "Math", 3, true);
            Course inactiveCourse = new Course(2L, "History", 4, false);
            Course activeCourse2 = new Course(3L, "Physics", 5, true);
            when(courseRepository.findAll()).thenReturn(List.of(activeCourse1, inactiveCourse, activeCourse2));

            List<Course> result = courseService.findActiveCourses();

            assertThat(result)
                    .hasSize(2)
                    .containsExactly(activeCourse1, activeCourse2);
            verify(courseRepository).findAll();
        }

        @Test
        @DisplayName("should return empty list when no courses are active")
        void shouldReturnEmptyListWhenNoActiveCourses() {
            Course inactive1 = new Course(1L, "History", 3, false);
            Course inactive2 = new Course(2L, "Art", 2, false);
            when(courseRepository.findAll()).thenReturn(List.of(inactive1, inactive2));

            List<Course> result = courseService.findActiveCourses();

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("should return all courses when all are active")
        void shouldReturnAllCoursesWhenAllActive() {
            Course c1 = new Course(1L, "CS", 4, true);
            Course c2 = new Course(2L, "Math", 3, true);
            when(courseRepository.findAll()).thenReturn(List.of(c1, c2));

            List<Course> result = courseService.findActiveCourses();

            assertThat(result).containsExactly(c1, c2);
        }

        @Test
        @DisplayName("should return empty list when repository returns empty list")
        void shouldReturnEmptyListWhenRepositoryReturnsEmpty() {
            when(courseRepository.findAll()).thenReturn(List.of());

            List<Course> result = courseService.findActiveCourses();

            assertThat(result).isEmpty();
            verify(courseRepository).findAll();
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("should return course when found")
        void shouldReturnCourseWhenFound() {
            Long id = 1L;
            Course expected = new Course(id, "Java", 5, true);
            when(courseRepository.findById(id)).thenReturn(Optional.of(expected));

            Course result = courseService.findById(id);

            assertThat(result).isEqualTo(expected);
            verify(courseRepository).findById(id);
        }

        @Test
        @DisplayName("should throw RuntimeException when course not found")
        void shouldThrowExceptionWhenNotFound() {
            Long id = 99L;
            when(courseRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> courseService.findById(id))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Course not found");

            verify(courseRepository).findById(id);
        }
    }

    @Nested
    @DisplayName("createCourse")
    class CreateCourseTests {

        @Test
        @DisplayName("should save course with correct attributes and return it")
        void shouldCreateAndSaveCourse() {
            String name = "Databases";
            int credits = 4;
            Course savedCourse = new Course(10L, name, credits, true);
            when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

            Course result = courseService.createCourse(name, credits);

            assertThat(result).isEqualTo(savedCourse);

            ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
            verify(courseRepository).save(captor.capture());
            Course captured = captor.getValue();

            assertThat(captured.getId()).isNull();
            assertThat(captured.getName()).isEqualTo(name);
            assertThat(captured.getCredits()).isEqualTo(credits);
            assertThat(captured.isActive()).isTrue();
        }

        @Test
        @DisplayName("should throw IllegalArgumentException when name is null")
        void shouldThrowExceptionWhenNameIsNull() {
            assertThatThrownBy(() -> courseService.createCourse(null, 3))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Course name is required");

            verifyNoInteractions(courseRepository);
        }

        @Test
        @DisplayName("should throw IllegalArgumentException when name is blank")
        void shouldThrowExceptionWhenNameIsBlank() {
            assertThatThrownBy(() -> courseService.createCourse("   ", 3))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Course name is required");

            verifyNoInteractions(courseRepository);
        }

        @Test
        @DisplayName("should throw IllegalArgumentException when credits is zero")
        void shouldThrowExceptionWhenCreditsIsZero() {
            assertThatThrownBy(() -> courseService.createCourse("Math", 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Credits must be positive");

            verifyNoInteractions(courseRepository);
        }

        @Test
        @DisplayName("should throw IllegalArgumentException when credits is negative")
        void shouldThrowExceptionWhenCreditsIsNegative() {
            assertThatThrownBy(() -> courseService.createCourse("Math", -1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Credits must be positive");

            verifyNoInteractions(courseRepository);
        }
    }

    @Nested
    @DisplayName("deleteCourse")
    class DeleteCourseTests {

        @Test
        @DisplayName("should call deleteById with correct id when course exists")
        void shouldDeleteExistingCourse() {
            Long id = 1L;
            Course course = new Course(id, "Algorithms", 5, true);
            when(courseRepository.findById(id)).thenReturn(Optional.of(course));

            courseService.deleteCourse(id);

            verify(courseRepository).findById(id);
            verify(courseRepository).deleteById(id);
        }

        @Test
        @DisplayName("should throw RuntimeException when course to delete does not exist")
        void shouldThrowExceptionWhenDeletingNonExistingCourse() {
            Long id = 1L;
            when(courseRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> courseService.deleteCourse(id))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Course not found");

            verify(courseRepository).findById(id);
            verify(courseRepository, never()).deleteById(any());
        }
    }
}