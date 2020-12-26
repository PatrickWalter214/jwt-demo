import java.util.Objects;

public class SampleJwt {

    private SampleJwtHeader header;
    private SampleJwtPayload payload;
    private boolean validSignature;

    public SampleJwtHeader getHeader() {
        return header;
    }

    public void setHeader(SampleJwtHeader header) {
        this.header = header;
    }

    public SampleJwtPayload getPayload() {
        return payload;
    }

    public void setPayload(SampleJwtPayload payload) {
        this.payload = payload;
    }

    public boolean isValidSignature() {
        return validSignature;
    }

    public void setValidSignature(boolean validSignature) {
        this.validSignature = validSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SampleJwt sampleJwt = (SampleJwt) o;
        return validSignature == sampleJwt.validSignature &&
                Objects.equals(header, sampleJwt.header) &&
                Objects.equals(payload, sampleJwt.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, payload, validSignature);
    }

    public static class SampleJwtHeader {
        private String alg;
        private String typ;

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }

        public String getTyp() {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SampleJwtHeader that = (SampleJwtHeader) o;
            return Objects.equals(alg, that.alg) &&
                    Objects.equals(typ, that.typ);
        }

        @Override
        public int hashCode() {
            return Objects.hash(alg, typ);
        }
    }

    public static class SampleJwtPayload {
        private String sub;
        private String name;
        private boolean admin;
        private int iat;

        public String getSub() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public int getIat() {
            return iat;
        }

        public void setIat(int iat) {
            this.iat = iat;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SampleJwtPayload that = (SampleJwtPayload) o;
            return admin == that.admin &&
                    iat == that.iat &&
                    Objects.equals(sub, that.sub) &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sub, name, admin, iat);
        }
    }
}
