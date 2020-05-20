package org.example;

import java.util.*;

public  class Rekords {
    public List<String> username= new ArrayList();
    public  List<Integer> score= new ArrayList();
    public  List<Integer> time= new ArrayList();

    public static <T extends Comparable<T>> void concurrentSort( final List<T> key, List<?>... lists){
        // Do validation
        if(key == null || lists == null)
            throw new NullPointerException("key cannot be null.");

        for(List<?> list : lists)
            if(list.size() != key.size())
                throw new IllegalArgumentException("all lists must be the same size");

        // Lists are size 0 or 1, nothing to sort
        if(key.size() < 2)
            return;

        // Create a List of indices
        List<Integer> indices = new ArrayList<Integer>();
        for(int i = 0; i < key.size(); i++)
            indices.add(i);

        // Sort the indices list based on the key
        Collections.sort(indices, new Comparator<Integer>(){
            @Override public int compare(Integer i, Integer j) {
                return key.get(i).compareTo(key.get(j));
            }
        });

        Map<Integer, Integer> swapMap = new HashMap<Integer, Integer>(indices.size());
        List<Integer> swapFrom = new ArrayList<Integer>(indices.size()),
                swapTo   = new ArrayList<Integer>(indices.size());

        // create a mapping that allows sorting of the List by N swaps.
        for(int i = 0; i < key.size(); i++){
            int k = indices.get(i);
            while(i != k && swapMap.containsKey(k))
                k = swapMap.get(k);

            swapFrom.add(i);
            swapTo.add(k);
            swapMap.put(i, k);
        }

        // use the swap order to sort each list by swapping elements
        for(List<?> list : lists)
            for(int i = 0; i < list.size(); i++)
                Collections.swap(list, swapFrom.get(i), swapTo.get(i));
    }
}
