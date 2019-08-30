package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonMessages {
    private final List<Message> list;
    private final int listRealSize;

    public JsonMessages(List<Message> sourceList, int fromIndex, int listRealSize) {
        this.listRealSize = listRealSize;
        this.list = new ArrayList<>();
        for (int i = fromIndex; i < sourceList.size(); i++)
            list.add(sourceList.get(i));
    }

    public List<Message> getList() {
        return Collections.unmodifiableList(list);
    }

    public int getListRealSize() {
        return listRealSize;
    }
}
