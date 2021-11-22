package com.ieng.shortenLink.service;

import com.ieng.shortenLink.entity.ShorterLink;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShorterService {
    List<ShorterLink> getAllLinks();
    ShorterLink createShorterUrl(ShorterLink shorter);
    ShorterLink updateShorterUrl(long id, ShorterLink shorter) throws Exception;
    void deleteShorterUrl(long id) throws Exception;
    ShorterLink findLinkByHash(String hash);
}
