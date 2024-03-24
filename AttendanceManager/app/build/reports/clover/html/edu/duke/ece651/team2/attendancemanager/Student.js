var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":36,"id":0,"methods":[{"el":13,"sc":3,"sl":8},{"el":19,"sc":3,"sl":14},{"el":22,"sc":3,"sl":20},{"el":23,"sc":3,"sl":23},{"el":26,"sc":3,"sl":24},{"el":27,"sc":3,"sl":27},{"el":30,"sc":3,"sl":28},{"el":31,"sc":3,"sl":31},{"el":34,"sc":3,"sl":32},{"el":35,"sc":3,"sl":35}],"name":"Student","sl":3}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
