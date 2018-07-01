package com.pagination;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class ListController {
	
		private static int currentPage = 1;
	    private static int pageSize = 5;
	    
	    
	    private static final int BUTTONS_TO_SHOW = 5;
	    private static final int INITIAL_PAGE = 0;
	    private static final int INITIAL_PAGE_SIZE = 5;
	    private static final int[] PAGE_SIZES = {5, 10, 20};

	    @Autowired
	    private BookService bookService;

    @RequestMapping(value = "/listBooks", method = RequestMethod.GET)
    public String listBooks(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<Book> bookPage = bookService.populateBooks(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "listBooks.html";
   
    }
    
    
    @GetMapping("/")
    public ModelAndView listBooks(@RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("page") Optional<Integer> page) {
    	
    	 ModelAndView modelAndView = new ModelAndView("listBooks");
    	 // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Book> books = bookService.populateBooks(PageRequest.of(evalPage, evalPageSize));
        Pager pager = new Pager(books.getTotalPages(), books.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("books", books);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
      
   
    }
}
