package com.nitan.agenticai;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nitan.agenticai.model.VectorDocument;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

class SimilaritySearchTest {

    @Test
    void shouldReturnMostSimilarDocument() {

      List<VectorDocument> store = List.of(
          // Quadrant I - Theme: Time Off & Benefits
          new VectorDocument("Employees have 25 vacation days", new double[]{0.85, 0.25}),
          new VectorDocument("Employees get 10 paid sick leaves annually", new double[]{0.70, 0.40}),

          // Quadrant II - Theme: Remote Work & Flexibility
          new VectorDocument("Remote work is fully supported globally", new double[]{-0.75, 0.35}),
          new VectorDocument("Flexible working hours allow custom schedules", new double[]{-0.60, 0.55}),

          // Quadrant III - Theme: Fees & Penalties
          new VectorDocument("Office parking spaces require a monthly fee", new double[]{-0.45, -0.65}),
          new VectorDocument("Lost keycards incur a replacement penalty fee", new double[]{-0.30, -0.75}),

          // Quadrant IV - Theme: Compensation & Bonuses
          new VectorDocument("Overtime hours are compensated at double rate", new double[]{0.65, -0.45}),
          new VectorDocument("Annual performance bonuses are paid in December", new double[]{0.80, -0.25})
      );

        double[] queryEmbedding = {0.8, 0.3};//How many vacation days do I get?

        VectorDocument bestMatch =
                store.stream()
                        .max(
                                Comparator.comparingDouble(
                                        doc -> cosineSimilarity(
                                                queryEmbedding,
                                                doc.embedding()
                                        )
                                )
                        )
                        .orElseThrow();

        assertEquals(
                "Employees have 25 vacation days",
                bestMatch.text()
        );
    }

    private static double cosineSimilarity(double[] a, double[] b) {
        double dot = 0;
        double normA = 0;
        double normB = 0;

        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}