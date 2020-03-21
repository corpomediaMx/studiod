package mx.corpomedia.studiod.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface indicacionesInterface {
    String JSONURL = "https://www.studiodlab.com.mx/admin/app/";

    @GET("indicaciones.php")
    Call<String> getString();
}
