package DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RideRetDTOMap {
   private int totalCount;
   private ArrayList<RideResponseDTO> results;

   public RideRetDTOMap(int totalCount, ArrayList<RideResponseDTO> results) {
      this.totalCount = totalCount;
      this.results    = results;
   }

   public RideRetDTOMap() {
   }

   public int getTotalCount() {
      return totalCount;
   }

   public void setTotalCount(int totalCount) {
      this.totalCount = totalCount;
   }

   public ArrayList<RideResponseDTO> getResults() {
      return results;
   }

   public void setResults(ArrayList<RideResponseDTO> results) {
      this.results = results;
   }
}
