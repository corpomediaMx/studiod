package mx.corpomedia.studiod.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DisenosInterface {

    String JSONURL = "https://www.studiodlab.com.mx/admin/app/";

    @GET("disenos.php")
    Call<String> getString(@Query("id") String id);
}
