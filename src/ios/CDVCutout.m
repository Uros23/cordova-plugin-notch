/********* NotchPlugin.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface CDVCutout : CDVPlugin {
  // Member variables go here.
}

- (void)has:(CDVInvokedUrlCommand*)command;
@end

@implementation CDVCutout

- (void)has:(CDVInvokedUrlCommand*)command
{
  CDVPluginResult* pluginResult = nil;

  BOOL hasNotch;
  int insetTop;
  hasNotch = NO;
  insetTop = 0;

  if (@available(iOS 11.0, *)) {
      UIWindow *window = UIApplication.sharedApplication.keyWindow;
      insetTop = window.safeAreaInsets.bottom;
      hasNotch = window.safeAreaInsets.bottom > 0;
  }

  NSDictionary *cutoutInfo = [NSDictionary dictionaryWithObjectsAndKeys: [NSNumber numberWithBool:hasNotch], @"cutout", [NSNumber numberWithInt:insetTop], @"insetTop", nil];

  pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:cutoutInfo];

  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
