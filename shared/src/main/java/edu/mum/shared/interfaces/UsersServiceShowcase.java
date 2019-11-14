package edu.mum.shared.interfaces;

import org.springframework.web.bind.annotation.PostMapping;

public interface UsersServiceShowcase {
    @PostMapping("/checkToken")
    boolean checkToken();
}
