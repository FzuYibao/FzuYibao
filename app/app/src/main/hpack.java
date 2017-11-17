package okhttp3.internal.http2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import okhttp3.internal.http2.hpackjson.Case;
import okhttp3.internal.http2.hpackjson.HpackJsonUtil;
import okhttp3.internal.http2.hpackjson.Story;
import okio.Buffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HpackDecodeTestBase {

  protected static Collection<Story[]> createStories(String[] interopTests)
      throws Exception {
    List<Story[]> result = new ArrayList<>();
    for (String interopTestName : interopTests) {
      List<Story> stories = HpackJsonUtil.readStories(interopTestName);
      if (stories.isEmpty()) {
        fail("No stories for: " + interopTestName);
      }
      for (Story story : stories) {
        result.add(new Story[] {story});
      }
    }
    return result;
  }

  private final Buffer bytesIn = new Buffer();
  private final Hpack.Reader hpackReader = new Hpack.Reader(4096, bytesIn);

  private final Story story;

  public HpackDecodeTestBase(Story story) {
    this.story = story;
  }

  protected void testDecoder() throws Exception {
    testDecoder(story);
  }

  protected void testDecoder(Story story) throws Exception {
    for (Case caze : story.getCases()) {
      bytesIn.write(caze.getWire());
      hpackReader.readHeaders();
      assertSetEquals(String.format("seqno=%d", caze.getSeqno()), caze.getHeaders(),
          hpackReader.getAndResetHeaderList());
    }
  }

  private static void assertSetEquals(
      String message, List<Header> expected, List<Header> observed) {
    assertEquals(message, new LinkedHashSet<>(expected), new LinkedHashSet<>(observed));
  }

  protected Story getStory() {
    return story;
  }
}