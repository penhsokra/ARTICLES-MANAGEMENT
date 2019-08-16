package camdev.sokra.topnews.callback;

import java.util.List;

import camdev.sokra.topnews.model.Articles;

public interface InteractorResponse<T> {
    void onSuccess(T dataResponse);
    void onComplete(String message);
    void onError(String message);

}
