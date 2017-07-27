package pl.spring.demo.common;

import pl.spring.demo.to.IdAware;

import java.util.Collection;

public class Sequence {

    public long nextValue(Collection<? extends IdAware> collectionIds) {
        long result = 1;
        for (IdAware nextExistingId : collectionIds) {
            if (Long.compare(nextExistingId.getId(), result) > 0) {
                result = nextExistingId.getId();
            }
        }
        return result+1;
    }
    
}
