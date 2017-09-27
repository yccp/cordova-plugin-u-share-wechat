#import "UShareWechat.h"

@implementation UShareWechat
- (void)pluginInitialize
{
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(finishLaunching:) name:UIApplicationDidFinishLaunchingNotification object:nil];
    
}

- (void)finishLaunching:(NSNotification *)notification
{
    // Put here the code that should be on the AppDelegate.m
    // 获取AppKey
    NSString *appKey = [[self.commandDelegate settings] objectForKey:@"ushare_wechat_app_key"];
    NSLog(@"Wechat appKey: %@", appKey);
    // 获取AppSecret
    NSString *appSecret = [[self.commandDelegate settings] objectForKey:@"ushare_wechat_app_secret"];
    NSLog(@"Wechat appSecret: %@", appSecret);
    
    /*
     设置微信的appKey和appSecret
     [微信平台从U-Share 4/5升级说明]http://dev.umeng.com/social/ios/%E8%BF%9B%E9%98%B6%E6%96%87%E6%A1%A3#1_1
     */
    [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_WechatSession appKey:appKey appSecret:appSecret redirectURL:nil];
    
    /*
     * 移除相应平台的分享，如微信收藏
     */
    [[UMSocialManager defaultManager] removePlatformProviderWithPlatformTypes:@[@(UMSocialPlatformType_WechatFavorite)]];
}

@end
