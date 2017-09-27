# 友盟社会化分享 cordova 插件 微信支持

> 请确保已安装 [友盟社会化分享 cordova 插件](https://github.com/yccp/cordova-plugin-u-share.git)

## 安装

```
cordova plugin add cordova-plugin-u-share-wechat --variable USHARE_WECHAT_APP_KEY=你的KEY --variable USHARE_WECHAT_APP_SECRET=你的SECRET --save
```
或
```
ionic cordova plugin add cordova-plugin-u-share-wechat --variable USHARE_WECHAT_APP_KEY=你的KEY --variable USHARE_WECHAT_APP_SECRET=你的SECRET
```

## 支付

```js
window.UShareWechat.sendPaymentRequest({
  partnerid: string; // merchant id
  prepayid: string; // prepay id
  noncestr: string; // nonce
  timestamp: string; // timestamp
  sign: string; // signed string
}, () => {
  console.log('success');
}, e => {
  console.error(e);
});

```