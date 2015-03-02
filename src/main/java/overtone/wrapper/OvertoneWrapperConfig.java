package overtone.wrapper;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = OvertoneWrapperConfig.Builder.class)
public class OvertoneWrapperConfig {
    public static final String DEFAULT_LIB_PATH = "./";

    private String nativeLibrariesPath;

    public OvertoneWrapperConfig(Builder builder) {
        this.nativeLibrariesPath = builder.nativeLibrariesPath.orElse(DEFAULT_LIB_PATH);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonProperty
    public String getNativeLibrariesPath() {
        return nativeLibrariesPath;
    }

    public static class Builder {
        private Optional<String> nativeLibrariesPath = Optional.empty();

        public Builder withNativeLibrariesPath(String nativeLibrariesPath) {
            this.nativeLibrariesPath = Optional.ofNullable(nativeLibrariesPath);
            return this;
        }

        public OvertoneWrapperConfig build() {
            return new OvertoneWrapperConfig(this);
        }
    }
}
