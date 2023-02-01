package fr.catcore.fabricatedforge.mixin.forgefml.client;

import cpw.mods.fml.client.FMLTextureFX;
import fr.catcore.fabricatedforge.mixininterface.IFMLTextureFX;
import fr.catcore.modremapperapi.api.mixin.ChangeSuperClass;
import net.minecraft.client.class_587;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_587.class)
@ChangeSuperClass(FMLTextureFX.class)
public class class_587Mixin implements IFMLTextureFX {

    @Shadow protected float[] field_2165;

    @Shadow protected float[] field_2166;

    @Shadow protected float[] field_2167;

    @Shadow protected float[] field_2168;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectCtr(CallbackInfo ci) {
        this.setup();
    }

    @Override
    public void setup() {
        this.callFMLSetup();
        this.field_2165 = new float[getThis().tileSizeSquare];
        this.field_2166 = new float[getThis().tileSizeSquare];
        this.field_2167 = new float[getThis().tileSizeSquare];
        this.field_2168 = new float[getThis().tileSizeSquare];
    }

    /**
     * @author forge
     * @reason fmltexturefx
     */
    @Overwrite
    public void method_1613() {
        for(int var1 = 0; var1 < getThis().tileSizeBase; ++var1) {
            for(int var2 = 0; var2 < getThis().tileSizeBase; ++var2) {
                float var3 = 0.0F;
                int var4 = (int)(MathHelper.sin((float)var2 * (float) Math.PI * 2.0F / 16.0F) * 1.2F);
                int var5 = (int)(MathHelper.sin((float)var1 * (float) Math.PI * 2.0F / 16.0F) * 1.2F);

                for(int var6 = var1 - 1; var6 <= var1 + 1; ++var6) {
                    for(int var7 = var2 - 1; var7 <= var2 + 1; ++var7) {
                        int var8 = var6 + var4 & getThis().tileSizeMask;
                        int var9 = var7 + var5 & getThis().tileSizeMask;
                        var3 += this.field_2165[var8 + var9 * getThis().tileSizeBase];
                    }
                }

                this.field_2166[var1 + var2 * getThis().tileSizeBase] = var3 / 10.0F
                        + (
                        this.field_2167[(var1 + 0 & getThis().tileSizeMask) + (var2 + 0 & getThis().tileSizeMask) * getThis().tileSizeBase]
                                + this.field_2167[(var1 + 1 & getThis().tileSizeMask) + (var2 + 0 & getThis().tileSizeMask) * getThis().tileSizeBase]
                                + this.field_2167[(var1 + 1 & getThis().tileSizeMask) + (var2 + 1 & getThis().tileSizeMask) * getThis().tileSizeBase]
                                + this.field_2167[(var1 + 0 & getThis().tileSizeMask) + (var2 + 1 & getThis().tileSizeMask) * getThis().tileSizeBase]
                )
                        / 4.0F
                        * 0.8F;
                this.field_2167[var1 + var2 * getThis().tileSizeBase] += this.field_2168[var1 + var2 * getThis().tileSizeBase] * 0.01F;
                if (this.field_2167[var1 + var2 * getThis().tileSizeBase] < 0.0F) {
                    this.field_2167[var1 + var2 * getThis().tileSizeBase] = 0.0F;
                }

                this.field_2168[var1 + var2 * getThis().tileSizeBase] -= 0.06F;
                if (Math.random() < 0.005) {
                    this.field_2168[var1 + var2 * getThis().tileSizeBase] = 1.5F;
                }
            }
        }

        float[] var11 = this.field_2166;
        this.field_2166 = this.field_2165;
        this.field_2165 = var11;

        for(int var2 = 0; var2 < getThis().tileSizeSquare; ++var2) {
            float var3 = this.field_2165[var2] * 2.0F;
            if (var3 > 1.0F) {
                var3 = 1.0F;
            }

            if (var3 < 0.0F) {
                var3 = 0.0F;
            }

            int var5 = (int)(var3 * 100.0F + 155.0F);
            int var6 = (int)(var3 * var3 * 255.0F);
            int var7 = (int)(var3 * var3 * var3 * var3 * 128.0F);
            if (getThis().field_2154) {
                int var8 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
                int var9 = (var5 * 30 + var6 * 70) / 100;
                int var10 = (var5 * 30 + var7 * 70) / 100;
                var5 = var8;
                var6 = var9;
                var7 = var10;
            }

            getThis().field_2152[var2 * 4 + 0] = (byte)var5;
            getThis().field_2152[var2 * 4 + 1] = (byte)var6;
            getThis().field_2152[var2 * 4 + 2] = (byte)var7;
            getThis().field_2152[var2 * 4 + 3] = -1;
        }
    }
}
