package so.onekey.app.wallet.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Caps Glide's image disk cache so cached token logos, NFT images and dApp
 * favicons can't grow unbounded on disk (the default Glide disk cache is only
 * ~250 MB but expo-image previously relied on no app-level configuration). 256 MB
 * matches the iOS SDImageCache ceiling configured in AppDelegate.swift.
 *
 * <p>expo-image loads through the Glide singleton, and Glide permits only one
 * {@code @GlideModule AppGlideModule} per APK. We take over from expo-image's
 * built-in one via {@code rootProject.ext.excludeAppGlideModule = true} in the
 * root build.gradle; expo-image's okhttp {@code LibraryGlideModule} is unaffected.
 */
@GlideModule
public final class OneKeyAppGlideModule extends AppGlideModule {
  private static final long DISK_CACHE_SIZE_BYTES = 256L * 1024L * 1024L;

  @Override
  public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    builder.setDiskCache(
        new InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_BYTES));
  }

  @Override
  public boolean isManifestParsingEnabled() {
    return false;
  }
}
