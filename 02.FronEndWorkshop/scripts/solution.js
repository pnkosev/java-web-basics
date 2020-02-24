const coursesNames = {
    fundamentals: 'Java Fundamentals',
    advanced: 'Java Advanced',
    db: 'Java DB',
    web: 'Java Web',
    htmlAndCss: 'HTML & CSS',
};

const educationTypesNames = {
    onSite: 'On site',
    online: 'Online',
};

const availableCourses = [
    { name: coursesNames.fundamentals, price: 170 },
    { name: coursesNames.advanced, price: 180 },
    { name: coursesNames.db, price: 190 },
    { name: coursesNames.web, price: 490 },
];

const educationTypes = [
    { name: educationTypesNames.onSite, discount: 0 },
    { name: educationTypesNames.online, discount: 0.06 },
];

const generateCourseItem = (course) => {
    const input = document.createElement('input');
    input.setAttribute('type', 'checkbox');
    input.name = course.name;
    input.value = course.name;

    const label = document.createElement('label');

    label.append(input);
    label.append(course.name);

    return label;
};

const generateEducationTypeItem = (educationTypes) => {
    const input = document.createElement('input');
    input.setAttribute('type', 'radio');

    input.name = 'presence';
    input.value = educationTypes.name;

    if (input.value === 'On site') {
        input.setAttribute('checked', true);
    } else if (input.value === 'Online') {
        input.setAttribute('id', 'online-presence');
    }

    const label = document.createElement('label');

    label.append(educationTypes.name);
    label.append(input);

    return label;
};

const generateList = (items, generateItemFunc) => {
    return items
        .map(course => generateItemFunc(course))
        .map(c => {
            const li = document.createElement('li');
            li.append(c);
            return li;
        });
};

const generateCourseList = () => {
    const ul = document.getElementById('available-courses');
    const items = generateList(availableCourses, generateCourseItem);
    items.forEach(item => ul.append(item));
};

const generateEducationTypeList = () => {
    const ul = document.getElementById('education-types');
    const items = generateList(educationTypes, generateEducationTypeItem);
    items.forEach(item => ul.append(item));
};

const createCourseWithText = (text) => {
    const li = document.createElement('li');
    li.innerText = text;
    return li;
};

const getSelectedCourses = () => {
    const courseNames = [...$('#available-courses input:checked')]
        .map(input => $(input).val());

    return courseNames
        .map(courseName => ({ ...availableCourses.find(course => course.name === courseName) }));
};

const getSelectedEducationType = () => {
    const educationTypeName = $('#education-types input:checked').val();
    return educationTypes.find(e => e.name === educationTypeName);
}

const getCourse = (courses, courseName) => courses.find(course => course.name === courseName);

const decorateCourses = (courses) => {
    const fundametalsCourse = getCourse(courses, coursesNames.fundamentals);
    const advancedCourse = getCourse(courses, coursesNames.advanced);
    const dbCourse = getCourse(courses, coursesNames.db);
    const webCourse = getCourse(courses, coursesNames.web);

    if (fundametalsCourse && advancedCourse) {
        // discount 10%
        advancedCourse.price *= 0.9;

        if (dbCourse) {
            fundametalsCourse.price *= 0.94;
            advancedCourse.price *= 0.94;
            dbCourse.price *= 0.94;

            if (webCourse) {
                courses.push({
                    name: coursesNames.htmlAndCss,
                    price: 0
                });
            }
        }
    }
};

const getMyCourseItem = (course) => course.name;

const generateMyCoursesList = (courses) => {
    const items = generateList(courses, getMyCourseItem);
    $('#my-courses').html('');
    items.forEach(item => $('#my-courses').append(item));
};


const calculateCourses = (e) => {
    e.preventDefault();
    e.stopPropagation();

    const courses = getSelectedCourses();
    const educationForm = getSelectedEducationType();

    decorateCourses(courses);
    generateMyCoursesList(courses);

    let price = courses.reduce((sum, curr) => sum += curr.price, 0);

    if (educationForm.name === educationTypesNames.online) {
        // discout 6%
        price *= 0.94;
    }

    $('#price-tag').html((Math.round((price + Number.EPSILON) * 100) / 100).toFixed(2));
    form.reset();
};

window.onload = (event) => {
    generateCourseList();
    generateEducationTypeList();

    document.getElementById('submit-btn').addEventListener('click', (e) => calculateCourses(e));
};
