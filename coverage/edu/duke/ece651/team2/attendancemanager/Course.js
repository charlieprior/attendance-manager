var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":261,"id":624,"methods":[{"el":52,"sc":5,"sl":45},{"el":70,"sc":3,"sl":63},{"el":89,"sc":3,"sl":82},{"el":96,"sc":5,"sl":94},{"el":103,"sc":5,"sl":101},{"el":110,"sc":5,"sl":108},{"el":117,"sc":5,"sl":115},{"el":124,"sc":5,"sl":122},{"el":131,"sc":5,"sl":129},{"el":138,"sc":5,"sl":136},{"el":149,"sc":5,"sl":143},{"el":158,"sc":5,"sl":156},{"el":165,"sc":5,"sl":163},{"el":173,"sc":5,"sl":171},{"el":177,"sc":5,"sl":175},{"el":184,"sc":5,"sl":182},{"el":193,"sc":5,"sl":189},{"el":203,"sc":5,"sl":195},{"el":213,"sc":5,"sl":205},{"el":228,"sc":5,"sl":222},{"el":243,"sc":5,"sl":240},{"el":248,"sc":5,"sl":245},{"el":252,"sc":5,"sl":250},{"el":260,"sc":5,"sl":254}],"name":"Course","sl":11}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":45},{"sl":115},{"sl":122},{"sl":222},{"sl":240}],"name":"testEndLecture","pass":true,"statements":[{"sl":46},{"sl":47},{"sl":48},{"sl":49},{"sl":50},{"sl":51},{"sl":116},{"sl":123},{"sl":223},{"sl":224},{"sl":225},{"sl":226},{"sl":227},{"sl":241},{"sl":242}]},"test_2":{"methods":[{"sl":82},{"sl":94},{"sl":129},{"sl":156},{"sl":182}],"name":"testPrintStudents","pass":true,"statements":[{"sl":83},{"sl":84},{"sl":85},{"sl":86},{"sl":87},{"sl":88},{"sl":95},{"sl":130},{"sl":157},{"sl":183}]},"test_21":{"methods":[{"sl":45},{"sl":129},{"sl":182},{"sl":189}],"name":"testaddStudents","pass":true,"statements":[{"sl":46},{"sl":47},{"sl":48},{"sl":49},{"sl":50},{"sl":51},{"sl":130},{"sl":183},{"sl":190},{"sl":191}]},"test_22":{"methods":[{"sl":45},{"sl":94},{"sl":101},{"sl":108},{"sl":129}],"name":"testReadNewCourse","pass":true,"statements":[{"sl":46},{"sl":47},{"sl":48},{"sl":49},{"sl":50},{"sl":51},{"sl":95},{"sl":102},{"sl":109},{"sl":130}]},"test_25":{"methods":[{"sl":82},{"sl":94},{"sl":163},{"sl":171}],"name":"testPrintLectures","pass":true,"statements":[{"sl":83},{"sl":84},{"sl":85},{"sl":86},{"sl":87},{"sl":88},{"sl":95},{"sl":164},{"sl":172}]},"test_27":{"methods":[{"sl":45},{"sl":115},{"sl":222}],"name":"testStartLecture","pass":true,"statements":[{"sl":46},{"sl":47},{"sl":48},{"sl":49},{"sl":50},{"sl":51},{"sl":116},{"sl":223},{"sl":224},{"sl":225},{"sl":226},{"sl":227}]},"test_36":{"methods":[{"sl":45},{"sl":63},{"sl":82},{"sl":94},{"sl":101},{"sl":108},{"sl":115},{"sl":129}],"name":"testConstructors","pass":true,"statements":[{"sl":46},{"sl":47},{"sl":48},{"sl":49},{"sl":50},{"sl":51},{"sl":64},{"sl":65},{"sl":66},{"sl":67},{"sl":68},{"sl":69},{"sl":83},{"sl":84},{"sl":85},{"sl":86},{"sl":87},{"sl":88},{"sl":95},{"sl":102},{"sl":109},{"sl":116},{"sl":130}]},"test_4":{"methods":[{"sl":82},{"sl":94},{"sl":101}],"name":"testPrintCourse","pass":true,"statements":[{"sl":83},{"sl":84},{"sl":85},{"sl":86},{"sl":87},{"sl":88},{"sl":95},{"sl":102}]},"test_9":{"methods":[{"sl":82},{"sl":182}],"name":"testPrintStudentStatuses","pass":true,"statements":[{"sl":83},{"sl":84},{"sl":85},{"sl":86},{"sl":87},{"sl":88},{"sl":183}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [22, 21, 36, 1, 27], [22, 21, 36, 1, 27], [22, 21, 36, 1, 27], [22, 21, 36, 1, 27], [22, 21, 36, 1, 27], [22, 21, 36, 1, 27], [22, 21, 36, 1, 27], [], [], [], [], [], [], [], [], [], [], [], [36], [36], [36], [36], [36], [36], [36], [], [], [], [], [], [], [], [], [], [], [], [], [9, 36, 25, 2, 4], [9, 36, 25, 2, 4], [9, 36, 25, 2, 4], [9, 36, 25, 2, 4], [9, 36, 25, 2, 4], [9, 36, 25, 2, 4], [9, 36, 25, 2, 4], [], [], [], [], [], [22, 36, 25, 2, 4], [22, 36, 25, 2, 4], [], [], [], [], [], [22, 36, 4], [22, 36, 4], [], [], [], [], [], [22, 36], [22, 36], [], [], [], [], [], [36, 1, 27], [36, 1, 27], [], [], [], [], [], [1], [1], [], [], [], [], [], [22, 21, 36, 2], [22, 21, 36, 2], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [2], [2], [], [], [], [], [], [25], [25], [], [], [], [], [], [], [25], [25], [], [], [], [], [], [], [], [], [], [21, 9, 2], [21, 9, 2], [], [], [], [], [], [21], [21], [21], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [1, 27], [1, 27], [1, 27], [1, 27], [1, 27], [1, 27], [], [], [], [], [], [], [], [], [], [], [], [], [1], [1], [1], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
