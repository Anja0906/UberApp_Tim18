package DTO;

import java.util.ArrayList;

public class MessageRetDTOMap {
    private int totalCount;
    private ArrayList<MessageResponseDTO> results;

    public MessageRetDTOMap(int totalCount, ArrayList<MessageResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<MessageResponseDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<MessageResponseDTO> results) {
        this.results = results;
    }
}
