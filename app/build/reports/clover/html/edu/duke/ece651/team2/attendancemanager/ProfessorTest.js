var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":66,"id":876,"methods":[{"el":22,"sc":5,"sl":13},{"el":29,"sc":5,"sl":24},{"el":36,"sc":5,"sl":31},{"el":43,"sc":5,"sl":38},{"el":65,"sc":5,"sl":45}],"name":"ProfessorTest","sl":7}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_12":{"methods":[{"sl":38}],"name":"testGetProfessorID","pass":true,"statements":[{"sl":40},{"sl":41},{"sl":42}]},"test_26":{"methods":[{"sl":45}],"name":"testCourses","pass":true,"statements":[{"sl":48},{"sl":49},{"sl":51},{"sl":52},{"sl":53},{"sl":54},{"sl":57},{"sl":58},{"sl":59},{"sl":62},{"sl":63},{"sl":64}]},"test_27":{"methods":[{"sl":31}],"name":"testGetEmail","pass":true,"statements":[{"sl":33},{"sl":34},{"sl":35}]},"test_33":{"methods":[{"sl":24}],"name":"testGetName","pass":true,"statements":[{"sl":26},{"sl":27},{"sl":28}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [33], [], [33], [33], [33], [], [], [27], [], [27], [27], [27], [], [], [12], [], [12], [12], [12], [], [], [26], [], [], [26], [26], [], [26], [26], [26], [26], [], [], [26], [26], [26], [], [], [26], [26], [26], [], []]
