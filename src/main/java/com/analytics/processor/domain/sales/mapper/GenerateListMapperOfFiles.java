package com.analytics.processor.domain.sales.mapper;

import com.analytics.processor.domain.sales.enums.IDType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class GenerateListMapperOfFiles {

    //TODO: Refatorar...
    public Map<IDType, List<String>> mapperType(String fileAsString) {
        Map<IDType, List<String>> map = new HashMap<>();

        Iterator<String> iterator = getIterator(fileAsString);

        while (iterator.hasNext()){
            String line = iterator.next();

            String[] columnsLine = line.split("ç");
            IDType idType = IDType.getBy(columnsLine[0]);

            map.computeIfAbsent(idType, key -> new ArrayList<>());

            line = concatWIthNextLine(map, iterator, line, idType);

            map.get(idType).add(line);
        }

        return map;
    }

    private Iterator<String> getIterator(String fileAsString) {
        List<String> filesList = Arrays.asList(fileAsString.split("\r\n"));
        Iterator<String> iterator;

        if(filesList.size()==1){
            iterator = Arrays.asList(fileAsString.split("\n")).iterator();
        }else{
            iterator = filesList.iterator();
        }
        return iterator;
    }

    private String concatWIthNextLine(Map<IDType, List<String>> map,
                                      Iterator<String> iterator, String line, IDType idType) {
        if(iterator.hasNext()){
            String nextLine = iterator.next();

            if (Objects.nonNull(idType) &&
                    !nextLine.startsWith("0")) {
                line = line.concat(nextLine);
            }else{
                map.get(idType).add(nextLine);
            }
        }

        return line;
    }

}
