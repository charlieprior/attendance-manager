var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":20,"id":362,"methods":[{"el":11,"sc":5,"sl":8},{"el":15,"sc":5,"sl":13},{"el":19,"sc":5,"sl":17}],"name":"University","sl":3}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_0":{"methods":[{"sl":8}],"name":"testReadNewCourse","pass":true,"statements":[{"sl":9},{"sl":10}]},"test_2":{"methods":[{"sl":8}],"name":"testPrintStudents","pass":true,"statements":[{"sl":9},{"sl":10}]},"test_3":{"methods":[{"sl":8},{"sl":13}],"name":"testReadNewProfessor","pass":true,"statements":[{"sl":9},{"sl":10},{"sl":14}]},"test_30":{"methods":[{"sl":8}],"name":"testPrintLectures","pass":true,"statements":[{"sl":9},{"sl":10}]},"test_32":{"methods":[{"sl":8}],"name":"testPrintStudentStatuses","pass":true,"statements":[{"sl":9},{"sl":10}]},"test_37":{"methods":[{"sl":8}],"name":"testPrintCourse","pass":true,"statements":[{"sl":9},{"sl":10}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [30, 0, 32, 2, 37, 3], [30, 0, 32, 2, 37, 3], [30, 0, 32, 2, 37, 3], [], [], [3], [3], [], [], [], [], [], []]
