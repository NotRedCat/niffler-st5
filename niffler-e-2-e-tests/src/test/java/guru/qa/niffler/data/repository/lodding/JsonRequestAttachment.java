package guru.qa.niffler.data.repository.lodding;

import io.qameta.allure.attachment.AttachmentData;

public class JsonRequestAttachment implements AttachmentData {

    private final String name;
    private final String json;

    public JsonRequestAttachment(String name, String json) {
        this.name = name;
        this.json = json;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getJson() {
        return json;
    }

}
