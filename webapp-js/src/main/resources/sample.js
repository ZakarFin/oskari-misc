function handleAction(params) {
    print("log message from JS");
    params.getResponse().getWriter().print("Hello JS! " + params.getUser().getLastname());
    params.getResponse().getWriter().flush();
    params.getResponse().getWriter().close();
}