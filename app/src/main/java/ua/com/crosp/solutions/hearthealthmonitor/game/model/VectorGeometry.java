package ua.com.crosp.solutions.hearthealthmonitor.game.model;

public class VectorGeometry {

    public static class VelocityVector {
        public float x = 0;
        public float y = 0;

        public VelocityVector(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void multiplyBy(float f) {
            this.x *= f;
            this.y *= f;
        }

        public void divideBy(float f) {
            this.x /= f;
            this.y /= f;
        }

        public void add(float f) {
            this.x += f;
            this.y += f;
        }

        public void addVector(VelocityVector vector) {
            this.x += vector.x;
            this.y += vector.y;
        }

        public void substract(float f) {
            this.x -= f;
            this.y -= f;
        }

        public void substractVector(VelocityVector vector) {
            this.x -= vector.x;
            this.y -= vector.y;
        }


        public float magnitude() {
            return length();
        }

        public float length() {
            return (float) Math.sqrt(x * x + y * y);
        }

        public float scalarProduct(VelocityVector vec) {
            return dotProduct(vec);
        }

        public float dotProduct(VelocityVector vec) {
            return (this.x * vec.x) + (this.y * vec.y);
        }

        public void rotateBy(float alpha) {
            float cos = (float) Math.cos(alpha);
            float sin = (float) Math.sin(alpha);
            float x = this.x * cos - this.y * sin;
            float y = this.x * sin + this.y * cos;
            this.x = x;
            this.y = y;
        }

        public void inverse() {
            this.x *= (-1);
            this.y *= (-1);
        }

        public void convertToUnitVector() {
            float l = this.length();
            this.x = this.x / l;
            this.y = this.y / l;
        }
    }

    public static VelocityVector randomVector() {
        float x = (float) (1 - (Math.random() * 2));
        float y = (float) (1 - (Math.random() * 2));
        VelocityVector v = new VelocityVector(x, y);
        v.convertToUnitVector();
        return v;
    }

    public static VelocityVector vectorFromTo(float x1, float y1, float x2, float y2) {
        return new VelocityVector(x2 - x1, y2 - y1);
    }

    public static float distanceFromTo(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;
        return (float) Math.sqrt(x * x + y * y);
    }

    public float angleFromTo(VelocityVector a, VelocityVector b) {
        float zaehler = a.dotProduct(b);
        float nenner = a.length() * b.length();
        return (float) Math.acos(zaehler / nenner);
    }
}
