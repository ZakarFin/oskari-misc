function handleAction(params) {
    print(params);
    for(var k in this) {
        print(k);
        print(this[k]);
    }
}