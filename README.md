### springMVC框架的简单实现

## 简介
> springMVC主要有九大组件，这里只实现了HandlerMapping和HandlerAdapte两大组件，其次springMVC存在HttpServletBean，FrameworkServlet以及DispatcherServlet三个servlet这里简化成了一个DispathcerServlet。

使用例子
```java
@MyController
@MyRequestMapping("/book")
public class BookController {
    @MyAutowired
    private BookService bookService;

    @MyRequestMapping("/get")
    public String get(@MyRequestParam("id") String id) {
        return bookService.get(id);
    }

    @MyRequestMapping("/add")
    public String add(@MyRequestParam("id") String id, @MyRequestParam("name") String name) {
        if (bookService.add(id, name)) {
            return "SUCCESS";
        } else {
            return "FAILED";
        }
    }

    @MyRequestMapping("/delete")
    public String delete(@MyRequestParam("id") String id) {
        if (bookService.delete(id)) {
            return "SUCCESS";
        } else {
            return "FAILED";
        }

    }

    @MyRequestMapping("/modify")
    public String modify(@MyRequestParam("id") String id, @MyRequestParam("name") String name) {
        if (bookService.modify(id, name)) {
            return "SUCCESS";
        } else {
            return "FAILED";
        }
    }

    @MyRequestMapping("/getAll")
    public String getAll() {
        return bookService.getAll().toString();
    }
}
```