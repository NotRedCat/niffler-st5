package guru.qa.niffler.data.repository.lodding;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import lombok.SneakyThrows;

public class JsonAllureAppender {
    private final String templateName = "json.ftl";
    private final AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();

    private final ObjectWriter jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    @SneakyThrows
    public void logJson(String name, Object json) {
        if (json != null) {
            String jsonStr = jsonWriter.writeValueAsString(json);
            processor.addAttachment(new JsonRequestAttachment(name, jsonStr),
                    new FreemarkerAttachmentRenderer(templateName));
        }
    }
}
