package jobGPT.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Builder
@Getter @Setter
@RequiredArgsConstructor
public class PostingDTO {
    private String name;
    private String href;
    private String title;

    @Builder
    public PostingDTO(String name, String href, String title) {
        this.name = name;
        this.href = href;
        this.title = title;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("href", href);
        jsonObject.put("title", title);
        return jsonObject;
    }
}
