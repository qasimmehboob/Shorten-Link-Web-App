package com.ieng.shortenLink.service.impl;

import com.ieng.shortenLink.common.CodeGenerator;
import com.ieng.shortenLink.common.Utility;
import com.ieng.shortenLink.entity.ShorterLink;
import com.ieng.shortenLink.repository.ShorterRepository;
import com.ieng.shortenLink.service.ShorterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShorterServiceImpl implements ShorterService {
    Logger logger = LoggerFactory.getLogger(ShorterServiceImpl.class);
    private final CodeGenerator codeGenerator;
    private ShorterRepository shorterRepository;
    @Value("${shorter.length}")
    private Integer shorterLength;

    @Autowired
    public ShorterServiceImpl(ShorterRepository shorterRepository) {
        this.codeGenerator = new CodeGenerator();
        this.shorterRepository = shorterRepository;
    }

    @Override
    public List<ShorterLink> getAllLinks() {
        List<ShorterLink> shorterLinks = shorterRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedDate"));
        return shorterLinks;
    }

    @Override
    @Transactional
    public ShorterLink updateShorterUrl(long id, ShorterLink shorter) throws Exception {
        Optional<ShorterLink> shorterLink = shorterRepository.findById(id);
        if(shorterLink.isPresent()) {
            shorterLink.get().setExpiryDate(shorter.getExpiryDate());
            shorterLink.get().setModifiedDate(new Date());
            return shorterRepository.save(shorter);
        }else {
            throw new Exception("Selected link is not present is system.");
        }
    }

    @Override
    public void deleteShorterUrl(long id) throws Exception {
        shorterRepository.deleteById(id);
    }

    @Override
    public ShorterLink findLinkByHash(String hash){
        Optional<ShorterLink> shorter = shorterRepository.findByHash(hash);
        if (shorter.isPresent()) {
            return shorter.get();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public ShorterLink createShorterUrl(ShorterLink shorter) {
        String hash = codeGenerator.generate(shorterLength);
        //logger.info("Hash: " + hash);
        if(hash != null) {
            String originalLink =  URLDecoder.decode(shorter.getOriginalLink(), StandardCharsets.UTF_8);
            logger.info("Original Url: " + originalLink);
            //String shorterLink = Utility.getDomainName(shorter.getOriginalLink()) + "/" + hash;
            String shorterLink = hash;
            logger.info("Shorter Url: " + shorterLink);
            Date expiryDate = null;
            if(shorter.getExpiryDate() == null) {
                Date currentDate = new Date();
                // convert date to calendar
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.WEEK_OF_YEAR, 1);
                expiryDate =  c.getTime();
            }else {
                expiryDate = shorter.getExpiryDate();
            }
            shorter = new ShorterLink(null, new Date(), new Date(), originalLink, shorterLink, expiryDate);
            return shorterRepository.save(shorter);
        }
        return null;
    }
}
